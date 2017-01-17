package com.crosstheborder.game.shared.ui;

import com.crosstheborder.game.shared.IGame;
import com.crosstheborder.game.shared.component.graphical.uigraphics.EndScreenGraphics;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderCountryTag;
import com.sstengine.country.CountryTag;
import com.sstengine.team.Team;
import com.sstengine.ui.UIObject;

import java.awt.*;
import java.io.File;
import java.rmi.RemoteException;

/**
 * Created by yannic on 10/01/2017.
 */
public class EndScreen extends UIObject {

    CountryTag<CrossTheBorderCountryTag> playerCountryTag;
    CountryTag<CrossTheBorderCountryTag> opposingCountryTag;
    private IGame game;
    private UIExtension ui;
    private int mexScore;
    private int usaScore;
    private boolean victory;
    private boolean isDraw = false;
    private String victoryText;
    private File victoryFlagFile;


    public int getMexScore() {
        return mexScore;
    }

    public int getUsaScore() {
        return usaScore;
    }

    public boolean isVictory() {
        return victory;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public String getVictoryText() {
        return victoryText;
    }

    public File getVictoryFlagFile() {
        return victoryFlagFile;
    }

    public EndScreen(IGame game, UIExtension ui) {
        super(new EndScreenGraphics(), new Rectangle(ui.getWidth(), ui.getHeight()));

        this.game = game;
        this.ui = ui;
        //get team of player
        playerCountryTag = ui.getPlayer().getTeam().getCountry().getTag();

        //get team scores
        try {
            for (Team team : game.getTeams()) {
                CountryTag<CrossTheBorderCountryTag> tag = team.getCountry().getTag();
                switch (tag.getTag()) {
                    case MEX:
                        mexScore = team.getScore();
                        break;
                    case USA:
                        usaScore = team.getScore();
                        break;
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //check if player won, or if game is a tie.
        switch (playerCountryTag.getTag()) {
            case MEX:
                victory = mexScore > usaScore;
                opposingCountryTag = CrossTheBorderCountryTag.USA;
                break;
            case USA:
                victory = usaScore > mexScore;
                opposingCountryTag = CrossTheBorderCountryTag.MEX;
                break;
        }
        if (mexScore == usaScore) {
            isDraw = true;
        }
        //set victory text and flag image:
        if (victory) {
            victoryText = "Victory";
            victoryFlagFile = new File("images" + File.separator + playerCountryTag.toString().toLowerCase() + ".png");
        } else if (!isDraw) {
            victoryText = "Defeat";
            victoryFlagFile = new File("images" + File.separator + opposingCountryTag.toString().toLowerCase() + ".png");
        } else {
            victoryText = "Draw";
            victoryFlagFile = new File("images" + File.separator + playerCountryTag.toString().toLowerCase() + ".png");
        }


    }

    //todo: handle clicking (continue button)
    @Override
    protected Object handleClick(int i, int i1) {
        return null;
    }

//    @Override
//    public int compareTo(Object o) {
//        return 0;
//    }

    public void loadValues() {
        //get team scores
        try {
            for (Team team : game.getTeams()) {
                CountryTag<CrossTheBorderCountryTag> tag = team.getCountry().getTag();
                switch (tag.getTag()) {
                    case MEX:
                        mexScore = team.getScore();
                        break;
                    case USA:
                        usaScore = team.getScore();
                        break;
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //check if player won, or if game is a tie.
        switch (playerCountryTag.getTag()) {
            case MEX:
                victory = mexScore > usaScore;
                opposingCountryTag = CrossTheBorderCountryTag.USA;
                break;
            case USA:
                victory = usaScore > mexScore;
                opposingCountryTag = CrossTheBorderCountryTag.MEX;
                break;
        }
        if (mexScore == usaScore) {
            isDraw = true;
        }
        //set victory text and flag image file:
        if (victory) {
            victoryText = "Victory";
            victoryFlagFile = new File("images" + File.separator + playerCountryTag.toString().toLowerCase() + ".png");
        } else if (!isDraw) {
            victoryText = "Defeat";
            victoryFlagFile = new File("images" + File.separator + opposingCountryTag.toString().toLowerCase() + ".png");
        } else {
            victoryText = "Draw";
            victoryFlagFile = new File("images" + File.separator + playerCountryTag.toString().toLowerCase() + ".png");
        }
    }


}
