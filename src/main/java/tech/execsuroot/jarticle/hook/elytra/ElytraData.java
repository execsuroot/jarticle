package tech.execsuroot.jarticle.hook.elytra;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;

@Getter
@Setter
@Configuration
public class ElytraData {

    private Component name;
    private Component lore;
    private int customModelData;
    private String animation;
}
