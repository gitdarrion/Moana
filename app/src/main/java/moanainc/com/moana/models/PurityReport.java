package moanainc.com.moana.models;

/**
 * Created by darrion on 2/28/17.
 */

public class PurityReport implements Report {

    private String _reportName;
    private String _dateCreated;
    private User _creator;
    private double _lat;
    private double _lng;


    public PurityReport(String reportName, String dateCreated, User creator, double lat, double lng){
        _reportName = reportName;
        _dateCreated = dateCreated;
        _creator = creator;
        _lat = lat;
        _lng = lng;

    }

    public PurityReport(){}

    public String getName() {
        return _reportName;
    }

    public String getDate(){
        return _dateCreated;
    }

    public User getCreator(){ return _creator; }

    public double getLat() { return _lat; }

    public double getLng() { return _lng; }

    public Report createReport(){
        return new PurityReport();
    }
}
