import org.junit.jupiter.api.Test;

public class RunTest {
    @Test
    public void runTestApi() {
        TestApiSteps.findInfoAboutMortySmith();
        TestApiSteps.findLastEpisode();
        TestApiSteps.findLastCharacterIDInLastEpisode();
        TestApiSteps.infoLastCharacterInLastEpisode();
        TestApiSteps.assertSpecies();
        TestApiSteps.assertLocation();
    }

}
