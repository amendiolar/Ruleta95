package mendiola.abel.ruleta.repositories;

import mendiola.abel.ruleta.datos.DatosDummy;
import mendiola.abel.ruleta.models.entities.Ruleta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RuletaRepositoryTest
{
    @Autowired
    private RuletaRepository ruletaRepository;

    @Test
    @DisplayName("Test: Crear ruleta")
    void crearRuleta(Ruleta entidad)
    {
        //Given
        ruletaRepository.save(DatosDummy.ruletacreacion01());
        ruletaRepository.save(DatosDummy.ruletacreacion02());
        ruletaRepository.save(DatosDummy.ruletacreacion03());

        //When

    }
}


    @Test
    @DisplayName("Test: Busca carreras por nombre")
    void findCarrerasByNombreContains()
    {
        //Given
		/*carreraRepository.save(DatosDummy.carrera01());
		carreraRepository.save(DatosDummy.carrera02());
		carreraRepository.save(DatosDummy.carrera03());*/

        //When
        Iterable<Carrera> expected = carreraRepository.findCarrerasByNombreContains("Sistemas");

        //Then
        assertThat(((List<Carrera>)expected).size() == 2).isTrue();
    }

    @Test
    @DisplayName("Test: Buscar carreras por nombre No case sensitive")
    void findCarrerasByNombreContainsIgnoreCase()
    {
        //Given
		/*carreraRepository.save(DatosDummy.carrera01());
		carreraRepository.save(DatosDummy.carrera02());
		carreraRepository.save(DatosDummy.carrera03());*/

        //When
        List<Carrera> expected = (List<Carrera>) carreraRepository.findCarrerasByNombreContainsIgnoreCase("sistemas");

        //Then
        assertThat(expected.size() == 2).isTrue();
    }

    @Test
    @DisplayName("Test: Buscar carreras mayores a N años")
    void findCarrerasByCantidadAniosAfter()
    {
        //Given
		/*carreraRepository.save(DatosDummy.carrera01());
		carreraRepository.save(DatosDummy.carrera02());
		carreraRepository.save(DatosDummy.carrera03());*/

        //When
        List<Carrera> expected = (List<Carrera>) carreraRepository.findCarrerasByCantidadAniosAfter(4);

        //Then
        assertThat(expected.size() == 2).isTrue();
}
