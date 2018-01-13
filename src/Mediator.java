import java.util.Collection;

/**
 * Created by mikouyou on 21/11/2017.
 */
public class Mediator {
    ExcelWrapper excWrap;

    public Collection<String> getResult(String query) {
        initWrappers();
        sentQueryToWrappers(query);
        return getResultFromWrappers();
    }

    public void sentQueryToWrappers(String query) {
        excWrap.getQueryFromMediator(query);
    }

    public Collection<String> getResultFromWrappers() {
        return excWrap.getQueryResult();
    }

    public void initWrappers() {
        excWrap = new ExcelWrapper();
    }
}
