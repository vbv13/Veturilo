import React, { Component } from 'react';

class Map extends Component {
    render() {
        return (
            <div className="map">
                <iframe title="Veturilo Map" src="https://www.veturilo.waw.pl/mapa-stacji/" width="100%" height="100%"/>
            </div>
        );
    }
}

export default Map;