package net.ddns.encante.telegram.HR.TelegramObjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuButtonCommands extends MenuButton {
    String type;
}
