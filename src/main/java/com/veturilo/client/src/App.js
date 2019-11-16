import React, { Component } from 'react';
import './App.css';
import * as StationsAPI from './StationsAPI';
import Stations from './Stations';
import Map from './Map';

class App extends Component {

  constructor() {
    super()
    this.state = {
      currentUserStations: []
    }
  }
  
  componentDidMount() {
    StationsAPI.getSelected().then((stations) => {
      this.setState({
        currentUserStations: stations
      })
    })
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
            <div className="panel left-side">
              <Stations
                stations={this.state.currentUserStations}
              />
            </div>
  
            <div className="panel right-side">
              <Map/>
            </div>
        </header>
      </div>
    );
  }

}

export default App;
