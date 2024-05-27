package com.app.yallagame.ae.util;

import com.app.yallagame.ae.api.APIClient;
import com.app.yallagame.ae.api.APIInterface;

import java.util.ArrayList;
import java.util.List;

public class AppManager {
    private static final AppManager ourInstance = new AppManager();
    public static AppManager getInstance() {
        return ourInstance;
    }

    public APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


//    public List<OleCountry> countries = new ArrayList<>();
   // public List<Parking> parkings = new ArrayList<>();
//    public List<OleClubFacility> clubFacilities = new ArrayList<>();
//    public OleFieldData oleFieldData = null;
    public int notificationCount = 0;

    private AppManager() {
    }
}
