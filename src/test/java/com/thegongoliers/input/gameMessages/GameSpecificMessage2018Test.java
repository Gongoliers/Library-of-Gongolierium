package com.thegongoliers.input.gameMessages;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameSpecificMessage2018Test {

    @Test
    public void testGetAllianceSwitch() {
        GameSpecificMessage2018 message2018 = new GameSpecificMessage2018("rll");
        assertEquals(GameSpecificMessage2018.Location.RIGHT, message2018.getAllianceSwitch());

        message2018 = new GameSpecificMessage2018("lll");
        assertEquals(GameSpecificMessage2018.Location.LEFT, message2018.getAllianceSwitch());
    }

    @Test
    public void testGetOpposingAllianceSwitch() {
        GameSpecificMessage2018 message2018 = new GameSpecificMessage2018("llr");
        assertEquals(GameSpecificMessage2018.Location.RIGHT, message2018.getOpposingAllianceSwitch());

        message2018 = new GameSpecificMessage2018("lll");
        assertEquals(GameSpecificMessage2018.Location.LEFT, message2018.getOpposingAllianceSwitch());
    }

    @Test
    public void testGetScale() {
        GameSpecificMessage2018 message2018 = new GameSpecificMessage2018("lrl");
        assertEquals(GameSpecificMessage2018.Location.RIGHT, message2018.getScale());

        message2018 = new GameSpecificMessage2018("lll");
        assertEquals(GameSpecificMessage2018.Location.LEFT, message2018.getScale());
    }
}