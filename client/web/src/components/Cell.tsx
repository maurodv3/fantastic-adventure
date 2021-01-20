import './Cell.css';

interface CellProps {
    value: number,
    marking: number,
    onClick: () => void,
    onRightClick: (mark: number) => void
}

function Cell(props: CellProps) {

    function selectDisplayValue() {
        const value: number = props.marking !== 0 ? props.marking : props.value;
        switch (value) {
            case -4:
                return "?";
            case -3:
                return "🚩";
            case -2:
            case 0:
                return "";
            case -1:
                return "💣";
            default:
                return value;
        }
    }

    function selectStyle() {
        const value: number = props.marking !== 0 ? props.marking : props.value;
        switch (value) {
            case -4:
                return " cell-question-mark";
            case -3:
                return " cell-flag-mark";
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
        if (props.marking !== 0) {
            return;
        }
        if (props.value === -2) {
            props.onClick();
        }
    }

    function onRightClick(event: React.MouseEvent) {
        event.preventDefault();
        props.onRightClick(nextMark(props.marking));
    }

    function nextMark(mark: number) : number {
        switch (mark) {
            case 0:
                return -3;
            case -3:
                return -4;
            case -4:
            default:
                return 0;
        }
    }

    const classes = "cell" +  selectStyle();

    return (
        <div className={classes} onClick={onClick} onContextMenu={onRightClick} data-testid="cell">
            {selectDisplayValue()}
        </div>
    );
}

export default Cell;
