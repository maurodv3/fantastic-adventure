import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('Renders React App', () => {
  render(<App />);
  const linkElement = screen.getByText(/Mauro's Minesweeper Game!/i);
  expect(linkElement).toBeInTheDocument();
});
