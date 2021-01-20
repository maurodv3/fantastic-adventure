import React from 'react';
import { render, screen } from '@testing-library/react';
import Cell from './Cell';

test('Renders Empty Cell', async () => {
    const fn = () => console.debug("Click!");
    render(<Cell value={-2} marking={0} onClick={fn} onRightClick={fn}/>);
    const element = screen.getByTestId('cell');
    expect(element).toBeInTheDocument();
    expect(element).toContainHTML("");
    expect(element.className).toBe("cell");
});

test('Renders Number Cell', async () => {
    const fn = () => console.debug("Click!");
    render(<Cell value={1} marking={0} onClick={fn} onRightClick={fn}/>);
    const element = screen.getByTestId('cell');
    expect(element).toBeInTheDocument();
    expect(element).toContainHTML("1");
    expect(element.className).toBe("cell cell-1");
});

test('Renders Mine Cell', async () => {
    const fn = () => console.debug("Click!");
    render(<Cell value={-1} marking={0} onClick={fn} onRightClick={fn}/>);
    const element = screen.getByTestId('cell');
    expect(element).toBeInTheDocument();
    expect(element).toContainHTML("💣");
    expect(element.className).toBe("cell cell-mine");
});

test('Renders Flag Cell', async () => {
    const fn = () => console.debug("Click!");
    render(<Cell value={-1} marking={-3} onClick={fn} onRightClick={fn}/>);
    const element = screen.getByTestId('cell');
    expect(element).toBeInTheDocument();
    expect(element).toContainHTML("🚩");
    expect(element.className).toBe("cell cell-flag-mark");
});

test('Renders Question Cell', async () => {
    const fn = () => console.debug("Click!");
    render(<Cell value={-1} marking={-4} onClick={fn} onRightClick={fn}/>);
    const element = screen.getByTestId('cell');
    expect(element).toBeInTheDocument();
    expect(element).toContainHTML("?");
    expect(element.className).toBe("cell cell-question-mark");
});
