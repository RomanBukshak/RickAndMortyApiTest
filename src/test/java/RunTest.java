import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RunTest {

    @Test
    @DisplayName("Rick And Morty API Test")
    public void runTestApiRickAndMorty() {
        TestApiStepsRickAndMorty.findInfoAboutMortySmith();
        TestApiStepsRickAndMorty.findLastEpisode();
        TestApiStepsRickAndMorty.findLastCharacterIDInLastEpisode();
        TestApiStepsRickAndMorty.infoLastCharacterInLastEpisode();
        TestApiStepsRickAndMorty.assertSpecies();
        TestApiStepsRickAndMorty.assertLocation();
    }

    @Test
    @DisplayName("Reqres API Test")
    public void runTestApiReqres() throws IOException {
        TestApiReqres.createUser();
    }

}
