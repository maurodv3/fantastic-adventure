package com.mauro.minesweeper.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Click action class to represent cell selection in the board game.
 */
@Schema(description = "Click action to execute in board game.")
public class ClickAction {

    @Schema(description = "Cell X position to click on.")
    private Integer xPosition;
    @Schema(description = "Cell Y position to click on.")
    private Integer yPosition;
    @Schema(description = "Cell is marked with a flag.")
    private Boolean isFlag;

    public ClickAction() {
    }

    public ClickAction(Integer xPosition, Integer yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public Integer getxPosition() {
        return xPosition;
    }

    public void setxPosition(Integer xPosition) {
        this.xPosition = xPosition;
    }

    public Integer getyPosition() {
        return yPosition;
    }

    public void setyPosition(Integer yPosition) {
        this.yPosition = yPosition;
    }

    public Boolean isFlag() {
        return isFlag;
    }

    public void setFlag(Boolean flag) {
        isFlag = flag;
    }

}
