package moanainc.com.moana.models;

/**
 * Created by darrion on 2/28/17.
 */

public class SourceReport implements Report {

    private String _reportName;
    private String _dateCreated;
    private User _creator;


    public SourceReport(String reportName, String dateCreated, User creator){
        _reportName = reportName;
        _dateCreated = dateCreated;
        _creator = creator;

    }

    public SourceReport(){}

    public String getName() {
        return _reportName;
    }

    public String getDate(){
        return _dateCreated;
    }

    public User getCreator(){
        return _creator;
    }

    public Report createReport(){
        return new SourceReport();
    }
}
