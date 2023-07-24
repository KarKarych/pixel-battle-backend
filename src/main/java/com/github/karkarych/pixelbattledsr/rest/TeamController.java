package com.github.karkarych.pixelbattledsr.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

  //список с пагинацией
  //создать команду (+ автодобавление создателя), изменить команду, удалить команду
  //присоединиться к команде, отсоединиться от команды
}
