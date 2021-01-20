import React from 'react';
import { render, screen } from '@testing-library/react';
import Board from "./Board";

test('Renders Fresh Board', async () => {
    const fn = () => console.debug("Click!");
    const board: number[][] = [
        [1, 2, 3], [4, 5, 6], [7, 8, 9]
    ];
    const markers: number[][] = [
        [0, 0, 0], [0, 0, 0], [0, 0, 0]
    ];
    render(<Board height={3} width={3} visibleCells={board} markers={markers} onClick={fn} onRightClick={fn}/>);
    const cells = await screen.findAllByTestId("cell");
    expect(cells.length).toBe(9);
    cells.forEach(cell => expect(cell).toContainHTML(""));
});
