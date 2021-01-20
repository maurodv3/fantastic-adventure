import React, { useState } from "react";
import Header from "./Header";
import Board from "./Board";
import './Game.css';

function Game() {

    const [height, setHeight] = useState(9);
    const [width, setWidth] = useState(9);
    const [mines, setMines] = useState(10);
    const [gameID, setGameID] = useState(null);
    const [started, setStarted] = useState(false);
    const [visible, setVisible] = useState<number[][]>([]) ;
    const [markers, setMarkers] = useState<number[][]>([]) ;
    const [finished, setFinished] = useState(false);
    const [won, setWon] = useState(false);

    const startGame = () => {

        fetch(`${process.env.REACT_APP_API_URL}/game/start?` + new URLSearchParams({
          "height": height.toString(),
          "width": width.toString(),
          "mines": mines.toString()
        }), {
            method: 'POST'
        })
        .then(response => response.json())
        .then(body => {
            console.log(body);
            setGameID(body.id);
            setVisible(body.visibleCells);
            setMarkers(body.markers);
            setStarted(!body.completed);
            setFinished(body.completed);
            setWon(body.winner);
        }).catch(console.error);
    }

    const onClick = (x: number, y: number) => {

        const body = JSON.stringify({
            xPosition: x,
            yPosition: y
        });

        fetch( `${process.env.REACT_APP_API_URL}/game/${gameID}/click`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
        })
        .then(response => response.json())
        .then(body => {
            console.log(body);
            setVisible(body.visibleCells);
            setMarkers(body.markers);
            setFinished(body.completed);
            setWon(body.winner);
        }).catch(console.error);
    }

    const onRightClick = (x: number, y: number, marking: number) => {

        console.log("Right!");

        const body = JSON.stringify({
            xPosition: x,
            yPosition: y,
            flag: marking === -3,
            questionMark: marking === -4,
            clearMarkings: marking === 0
        });

        console.log(body);

        fetch( `${process.env.REACT_APP_API_URL}/game/${gameID}/click`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
        })
        .then(response => response.json())
        .then(body => {
            console.log(body);
            setVisible(body.visibleCells);
            setMarkers(body.markers);
            setFinished(body.completed);
            setWon(body.winner);
        }).catch(console.error);
    }

    const winningMessage = () => {
        if (won) {
            return <p>Congratulations you have won!. &#128512;</p>
        } else {
            return <p>Sorry you have lost!. &#128577;</p>
        }
    }

    const reset = () => {
        setStarted(false);
        setVisible([]);
        setMarkers([]);
        setFinished(false);
        setWon(false);
    }

    return (
      <div className="game">
          <Header/>
          {started ? (
              <Board height={height} width={width} visibleCells={visible} markers={markers} onClick={onClick} onRightClick={onRightClick}/>
          ) : (
              <div className="start">
                  <button className="button" onClick={startGame}>Start new game!</button>
                  <div className="customize-panel">
                      <p>Customize your game!</p>
                      <div className="input-group">
                          <label className="label">Height</label>
                          <input className="input" type="number" id="height" min={8} max={30}
                                 value={height} onChange={(event => setHeight(parseInt(event.currentTarget.value)))}/>
                      </div>
                      <div className="input-group">
                          <label className="label">Width</label>
                          <input className="input" type="number" id="width" min={8} max={30}
                                 value={width} onChange={(event => setWidth(parseInt(event.currentTarget.value)))}/>
                      </div>
                      <div className="input-group">
                          <label className="label">Mines</label>
                          <input className="input" type="number" id="mines" min={1} max={99}
                                 value={mines} onChange={(event => setMines(parseInt(event.currentTarget.value)))}/>
                      </div>
                  </div>

              </div>
          )}
          { finished ? (
              <div className="message-panel">
                  {winningMessage()}
                  <button className="button" onClick={reset}>Play Again!</button>
              </div>
          ) : null }
      </div>
    );
}

export default Game;
