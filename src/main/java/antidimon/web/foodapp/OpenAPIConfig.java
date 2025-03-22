package antidimon.web.foodapp;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Calories API",
                description = "API для подсчёта каллорий",
                version = "1.0.0",
                contact = @Contact(
                        name = "Ивасенко Дмитрий",
                        email = "ivasenkodiman@mail.ru"
                )
        )
)
public class OpenAPIConfig {
}
