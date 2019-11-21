import React, { Component } from 'react';
import Station from './Station';
import PropTypes from 'prop-types';

class Stations extends Component {
    render() {
        return (
            <div className="stations">
                <ul className="stations-grid">
                    {this.props.stations &&
                        this.props.stations.map((station) => (
                            <li key={station.id} className="station-list-item">
                                {station.id ? <Station station={station} /> : null}
                            </li>
                        ))
                    }
                    
                </ul>
            </div>
        );
    }
}

 Stations.propTypes ={
     station: PropTypes.object.isRequired
}

export default Stations;