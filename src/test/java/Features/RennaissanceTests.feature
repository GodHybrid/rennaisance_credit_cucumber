#language: ru
Функционал: Вклады

  @all @success @scenario_1
  Сценарий: Вклад на полгода

    Когда выбрано меню "Вклады"
    И выбрана валюта "рубли"

    Когда заполнены поля:
      |Сумма вклада|300000|
      |Ежемесячное пополнение|50000|

    Когда выбран срок вклада "6 месяцев"
    Когда нажата кнопка "Ежемесячная капитализация"
    
    Тогда значение поля "К снятию" равно "559 988,81"

  @all @success @scenario_2
  Сценарий: Вклад на 9 месяцев

    Когда выбрано меню "Вклады"
    И выбрана валюта "рубли"

    Когда заполнены поля:
      |Сумма вклада|500000|
      |Ежемесячное пополнение|70000|

    Когда выбран срок вклада "9 месяцев"
    Когда нажата кнопка "Ежемесячная капитализация"

    Тогда значение поля "К снятию" равно "1 088 175,53"