package com.github.karkarych.pixelbattledsr.service.logic.impl;

import com.github.karkarych.pixelbattledsr.db.entity.Field;
import com.github.karkarych.pixelbattledsr.db.entity.FieldId;
import com.github.karkarych.pixelbattledsr.db.entity.User;
import com.github.karkarych.pixelbattledsr.db.repository.FieldRepository;
import com.github.karkarych.pixelbattledsr.db.repository.UserRepository;
import com.github.karkarych.pixelbattledsr.redis.UserCache;
import com.github.karkarych.pixelbattledsr.redis.UserCacheRepository;
import com.github.karkarych.pixelbattledsr.service.logic.FieldService;
import com.github.karkarych.pixelbattledsr.service.logic.WebsocketService;
import com.github.karkarych.pixelbattledsr.service.model.field.CoordinatesRequest;
import com.github.karkarych.pixelbattledsr.service.model.field.FieldResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.karkarych.pixelbattledsr.service.exception.model.FieldException.Code.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FieldServiceImpl implements FieldService {

  @Value("${pixel-battle.create-new}")
  private boolean createNew;
  @Value("${pixel-battle.rows}")
  private int rows;
  @Value("${pixel-battle.columns}")
  private int columns;
  @Value("${pixel-battle.request-timeout}")
  private int requestTimeout;

  private final UserRepository userRepository;
  private final UserCacheRepository userCacheRepository;
  private final FieldRepository fieldRepository;
  private final WebsocketService websocketService;

  @Override
  @Transactional(readOnly = true)
  public FieldResponse getField() {
    String[][] field = new String[rows][columns];

    List<Field> fieldPoints = fieldRepository.findAll();
    for (Field point : fieldPoints) {
      FieldId id = point.getId();
      field[id.getRow()][id.getColumn()] = point.getColor();
    }

    return new FieldResponse(field);
  }

  @Override
  @Transactional
  public void saveCoordinates(CoordinatesRequest request, String username) {
    int row = request.row();
    int column = request.column();
    String color = request.color();

    if (row < 0 || row > rows) throw FIELD_ROW_OUT_OF_RANGE.get();
    if (column < 0 || column > columns) throw FIELD_COLUMN_OUT_OF_RANGE.get();

    UUID userId = userRepository.findByLogin(username)
      .map(User::getId)
      .orElseThrow();

    Instant currentAccessDate = Instant.now();

    Optional<UserCache> userCacheOpt = userCacheRepository.findById(userId.toString());
    if (userCacheOpt.isPresent()) {
      Instant lastAccessDate = userCacheOpt.get().getLastAccessDate();
      long durationBetweenClientAccesses = Duration.between(lastAccessDate, currentAccessDate).getSeconds();
      if (durationBetweenClientAccesses < requestTimeout) {
        throw FIELD_TOO_MANY_REQUEST_UPDATE.get();
      }
    }

    Field field = fieldRepository.findById(new FieldId(row, column))
      .orElseThrow();

    UUID ownerId = field.getOwnerId();
    if (ownerId == null) {
      userRepository.increaseCapturedCellsById(userId);
    } else if (!ownerId.equals(userId)) {
      userRepository.decreaseCapturedCellsById(ownerId);
      userRepository.increaseCapturedCellsById(userId);
    }

    userCacheRepository.save(new UserCache(userId.toString(), currentAccessDate));

    field.setColor(color);
    field.setOwnerId(userId);

    websocketService.sendUpdatedField(request);
  }

  @Transactional
  @EventListener(ContextRefreshedEvent.class)
  public void createField() {
    if (!createNew) return;

    fieldRepository.deleteAll();

    List<Field> allFields = new ArrayList<>(rows * columns);
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        FieldId fieldId = new FieldId(row, column);
        allFields.add(new Field(fieldId, "#ffffff", null));
      }
    }

    fieldRepository.saveAll(allFields);
  }
}
