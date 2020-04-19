
//Pham Thi Hue
//S1839331

package com.example.trafficscotland;

import java.util.Date;

public class objectTrafficScotland {
    String georsspoint;
    String title;
    String description;
    String startDate;
    String endDate;
    String date;

    String link;

    public objectTrafficScotland(){

    }

    public objectTrafficScotland(String title, String description, String startDate, String endDate, String Date, String georsspoint, String link)
    {
        this.title=title;
        this.description=description;
        this.startDate=startDate;
        this.endDate=endDate;
        this.date=date;
        this.georsspoint=georsspoint;
        this.link=link;
    }
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public String getStartDate()
    {
        return startDate;
    }
    public String getEndDate() { return endDate; }
    public String getDate()
    {
        return date;
    }
    public String getGeorsspoint()
    {
        return georsspoint;
    }
    public String getLink()
    {
        return link;
    }

    public void setTitle(String title)
    {
        this.title=title;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
    public void setStartDate(String startDate)
    {
        this.startDate=startDate;
    }
    public void setEndDate(String endDate)
    {
        this.endDate=endDate;
    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public void setGeorsspoint(String georsspoint)
    {
        this.georsspoint=georsspoint;
    }
    public void setLink(String link)
    {
        this.link=link;
    }

}

