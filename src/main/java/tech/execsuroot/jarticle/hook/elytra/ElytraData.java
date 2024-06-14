package tech.execsuroot.jarticle.hook.elytra;

import de.exlll.configlib.Configuration;
import lombok.*;
import net.kyori.adventure.text.Component;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class ElytraData {

    private Component name;
    private Component lore;
    private int customModelData;
    private String animation;
}
