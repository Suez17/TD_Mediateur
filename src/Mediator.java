/**
 * Created by mikouyou on 21/11/2017.
 */
public class Mediator {
    ExcelWrapper excWrap = new ExcelWrapper();

    public void sentQueryToWrappers(String query) {
        excWrap.getQueryFromMediator(query);
    }

    public String getResultFromWrappers() {
        return excWrap.getQueryResult();
    }
}
