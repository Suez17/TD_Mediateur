/**
 * Created by mikouyou on 21/11/2017.
 */
public class Mediator {

    public void sentQueryToWrappers(String query) {
        ExcelWrapper excWrap= new ExcelWrapper();
        excWrap.excuteQueryInExcel(query);
    }
}
