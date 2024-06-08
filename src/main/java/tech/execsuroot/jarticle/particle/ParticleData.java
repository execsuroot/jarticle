package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

@Getter
@Setter
@Configuration
public class ParticleData {

    public static final Vector POSITION_ZERO = new Vector(0, 0, 0);

    private Vector position = POSITION_ZERO;
    private Vector offset = POSITION_ZERO;
    private Particle type;
    private int amount = 1;
    private double speed = 1.0;
    private String data = null;
}
