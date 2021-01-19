import React from "react";
import './Cell.css';

interface CellProps {
    value: number,
    onClick: () => void
}

function Cell(props: CellProps) {

    function selectDisplayValue() {
        switch (props.value) {
            case -2:
            case 0:
                return "";
            case -1:
                return "💣";
            default:
                return props.value;
        }
    }

    function selectStyle() {
        switch (props.value) {
            case -2:
                return "";
            case 0:
                return " cell-blank";
            case -1:
                return " cell-mine";
            default:
                return " cell-" + props.value;
        }
    }

    function onClick()  {
        if (props.value === -2) {
            props.onClick();
        }
    }

    const classes = "cell" +  selectStyle();

    return (
        <div className={classes} onClick={onClick}>
            {selectDisplayValue()}
        </div>
    );
}

export default Cell;
