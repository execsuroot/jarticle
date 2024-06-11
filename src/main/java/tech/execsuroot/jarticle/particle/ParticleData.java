package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Particle;

@Getter
@Setter
@Configuration
public class ParticleData {

    private ParticlePosition position = ParticlePosition.ZERO;
    private ParticlePosition offset = ParticlePosition.ZERO;
    private Particle type;
    private int amount = 1;
    private double speed = 1.0;
    private String data = null;
}
