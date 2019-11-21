import React, { Component } from 'react';
import Graph from './Graph';

class Station extends Component {
    render() {
        return (
            <div className="Station">
                {console.log(this.props)}
                <h5 className="station-name">{this.props.station.name}</h5>
                <h5 className="station-freeBikes">Wolne rowery:{this.props.station.freeBikes}</h5>
                <Graph
                    stationId = {this.props.station.id}
                />
            </div>
        );
    }
}

export default Station;