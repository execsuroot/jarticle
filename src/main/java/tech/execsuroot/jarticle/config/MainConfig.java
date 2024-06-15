package tech.execsuroot.jarticle.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
import tech.execsuroot.jarticle.hook.bow.BowData;
import tech.execsuroot.jarticle.hook.elytra.ElytraData;
import tech.execsuroot.jarticle.particle.AnimationData;
import tech.execsuroot.jarticle.particle.FrameData;
import tech.execsuroot.jarticle.particle.ParticleData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the main configuration file.
 * It is used to store general configuration options.
 */
@Getter
@Setter
@Configuration
public class MainConfig {

    /**
     * The instance of the main configuration file.
     * Use to access the configuration.
     */
    @Getter
    @Setter
    private static MainConfig instance;

    @Comment("Animation declarations to use in other parts of the configuration.")
    private Map<String, AnimationData> animations = Map.of(
            "flame-trail", AnimationData.builder()
                    .frames(List.of(
                            FrameData.builder()
                                    .particles(List.of(
                                            ParticleData.builder()
                                                    .type(Particle.FLAME)
                                                    .build()
                                    ))
                                    .duration(20)
                                    .build(),
                            FrameData.builder()
                                    .particles(List.of(
                                            ParticleData.builder()
                                                    .type(Particle.SOUL_FIRE_FLAME)
                                                    .build()
                                    ))
                                    .duration(20)
                                    .build()
                    ))
                    .build()
    );
    @Comment({
            "Permission animations.",
            "The key is the permission, the value is the animation to use.",
            "This will always play an animation for a player with the permission."
    })
    private Map<String, String> permissions = Map.of(
            "jarticle.fire-wings", "flame-trail"
    );
    @Comment({
            "Elytra animations.",
            "This will play animation during the elytra flight.",
    })
    private Map<String, ElytraData> elytras = Map.of(
            "fire-wings", ElytraData.builder()
                    .name(Component.text("Flame Wings").color(NamedTextColor.RED))
                    .lore(Component.text("These wings are on fire!").color(NamedTextColor.GOLD))
                    .customModelData(29001)
                    .animation("flame-trail")
                    .build()
    );
    @Comment({
            "Bow animations.",
            "This will play animation for the arrow shot by the bow.",
    })
    private Map<String, BowData> bows = Map.of(
            "fire-bow", BowData.builder()
                    .name(Component.text("Fire Bow").color(NamedTextColor.RED))
                    .lore(Component.text("This bow is on fire!").color(NamedTextColor.GOLD))
                    .customModelData(29001)
                    .animation("flame-trail")
                    .build()
    );
}
