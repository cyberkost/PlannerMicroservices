package ru.javabegin.micro.planner.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.micro.planner.entity.Stat;
import ru.javabegin.micro.planner.todo.service.StatService;


/*

Чтобы дать меньше шансов для взлома (например, CSRF атак): POST/PUT запросы могут изменять/фильтровать закрытые данные, а GET запросы - для получения незащищенных данных
Т.е. GET-запросы не должны использоваться для изменения/получения секретных данных

Если возникнет exception - вернется код  500 Internal Server Error, поэтому не нужно все действия оборачивать в try-catch

Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON

Названия методов могут быть любыми, главное не дублировать их имена и URL mapping

*/

@RestController

public class StatController {

    private final StatService service; // сервис для доступа к данным (напрямую к репозиториям не обращаемся)

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public StatController(StatService service) {
        this.service = service;
    }

    // для статистика всгда получаем только одну строку с id=1 (согласно таблице БД)
    @PostMapping("/stat")
    public ResponseEntity<Stat> findByUserId(@RequestBody Long userId) {

        // можно не использовать ResponseEntity, а просто вернуть коллекцию, код все равно будет 200 ОК
        return ResponseEntity.ok(service.findStat(userId));
    }
}