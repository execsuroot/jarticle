package tech.execsuroot.jarticle.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Particle;
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

    private Map<String, AnimationData> animations = Map.of(
            "example", exampleAnimationData()
    );

    private static AnimationData exampleAnimationData() {
        AnimationData animation = new AnimationData();
        List<FrameData> frames = new ArrayList<>();
        animation.setFrames(frames);

        ParticleData flameParticle = new ParticleData();
        flameParticle.setType(Particle.FLAME);
        ParticleData heartParticle = new ParticleData();
        heartParticle.setType(Particle.HEART);
        heartParticle.setAmount(2);

        FrameData firstFrame = new FrameData();
        firstFrame.setParticles(List.of(flameParticle));
        firstFrame.setDuration(20);
        frames.add(firstFrame);

        FrameData secondFrame = new FrameData();
        secondFrame.setParticles(List.of(heartParticle));
        secondFrame.setDuration(20);
        frames.add(secondFrame);

        return animation;
    }
}
