package tech.execsuroot.jarticle.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import de.exlll.configlib.Ignore;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Particle;
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
            "flame-trail", flameTrailAnimation()
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
            "fire-wings", fireWingsElytraData()
    );

    private static ElytraData fireWingsElytraData() {
        ElytraData data = new ElytraData();
        data.setName(Component.text("Flame Wings").color(NamedTextColor.RED));
        data.setLore(Component.text("These wings are on fire!").color(NamedTextColor.GOLD));
        data.setCustomModelData(29001);
        data.setAnimation("flame-trail");
        return data;
    }

    private static AnimationData flameTrailAnimation() {
        AnimationData animation = new AnimationData();
        List<FrameData> frames = new ArrayList<>();
        animation.setFrames(frames);

        ParticleData flameParticle = new ParticleData();
        flameParticle.setType(Particle.FLAME);
        ParticleData soulFlameParticle = new ParticleData();
        soulFlameParticle.setType(Particle.SOUL_FIRE_FLAME);

        FrameData flameFrame = new FrameData();
        flameFrame.setParticles(List.of(flameParticle));
        flameFrame.setDuration(20);
        frames.add(flameFrame);

        FrameData soulFlameFrame = new FrameData();
        soulFlameFrame.setParticles(List.of(soulFlameParticle));
        soulFlameFrame.setDuration(20);
        frames.add(soulFlameFrame);

        return animation;
    }
}
