import React from 'react';
import Cell from "./Cell";
import './Board.css';

interface BoardProps {
    height: number,
    width: number,
    visibleCells: number[][],
    markers: number[][],
    onClick: (x: number, y: number) => void,
    onRightClick: (x: number, y: number, marking: number) => void
}

function Board(props: BoardProps) {

    const { height, width, visibleCells, markers } = props;

    function buildLine(line: number, width: number) {
        let cells = [];
        for (let i = 0; i < width; i++) {
            const key = `${line}-${i}`;
            cells.push(
                <Cell key={key}
                      value={visibleCells[line][i]}
                      marking={markers[line][i]}
                      onClick={() => props.onClick(line, i)}
                      onRightClick={(mark: number) => props.onRightClick(line, i, mark)}
                />);
        }
        return cells;
    }

    function buildBoard(height: number, width: number) {
        let rows = [];
        for (let i = 0; i < height; i++) {
            rows.push(
                <div key={i} className="row">
                    {buildLine(i, width)}
                </div>
            )
        }
        return rows;
    }

    return (
        <div className="board">
            {buildBoard(height, width)}
        </div>
    )

}

export default Board;
