import React from 'react';
import { render, screen } from '@testing-library/react';
import Game from "./Game";

test('Renders Minesweeper game', () => {
    render(<Game/>);
    const startButton = screen.getByText(/Start new game!/i);
    expect(startButton).toBeInTheDocument();
    expect(screen.queryByText(/Congratulations you have won!. &#128512;/i)).toBeNull();
    expect(screen.queryByText(/Sorry you have lost!. &#128577;/i)).toBeNull();
});
