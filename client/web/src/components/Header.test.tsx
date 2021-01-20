import React from 'react';
import { render, screen } from '@testing-library/react';
import Header from "./Header";

test('Renders React App', () => {
    render(<Header />);
    const linkElement = screen.getByText(/Mauro's Minesweeper Game!/i);
    expect(linkElement).toBeInTheDocument();
});
