package simple.json;

/**
 * Created by faizan on 24/02/2017.
 */

public class SeachModel {


  /*  public SeachModel(String searchMName, String searchLocation, String searchfavourate) {
        this.searchMName = searchMName;
        this.searchLocation = searchLocation;
        this.searchfavourate = searchfavourate;
    }
*/
    String searchMName;
     String searchLocation;
     String searchfavourate;
  //  List<SeachModel> seachModels;

    public SeachModel(){}

    public String getSearchMName() {
        return searchMName;
    }

    public void setSearchMName(String searchMName) {
        this.searchMName = searchMName;
    }

    public String getSearchLocation() {
        return searchLocation;
    }

    public void setSearchLocation(String searchLocation) {
        this.searchLocation = searchLocation;
    }

    public String getSearchfavourate() {
        return searchfavourate;
    }

    public void setSearchfavourate(String searchfavourate) {
        this.searchfavourate = searchfavourate;
    }




}
