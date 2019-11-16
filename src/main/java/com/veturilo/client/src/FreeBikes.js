import React, { Component } from 'react';
import * as StationsAPI from './StationsAPI';

class FreeBikes extends Component {

    constructor() {
        super()
        this.state = {
          freeBikesOnStation: []
        }
      }

    componentDidMount() {
        StationsAPI.freeBikes(this.props.stationId).then((bikes) => {
            this.setState({
                freeBikesOnStation: bikes
            })
        })
    }
    
    getStationFreeBikes = () => {
        document.querySelector('.station-freeBikes').addEventListener('onlick', function (e){
            
        })
    }


    render() {
        return (
            <div>
                <div class="modalSingleUser">

                </div>
            </div>
        );
    }
}

export default FreeBikes;