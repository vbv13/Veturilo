import React, { Component } from 'react';
import * as StationsAPI from './StationsAPI';
import * as CanvasJSReact from './canvasjs.react';
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

class Graph extends Component {

    constructor() {
        super();
        this.state = {
            oneOfstation: null
        }
      }    

    componentDidMount() {
        StationsAPI.graphData(this.props.stationId).then((points) => {
            this.setState({
                oneOfstation: points
            })
        })
    }

    handlePoints(points){
        if(points == null) {
            return 
        } else {
            return points.map((point) => {
                return { x: new Date(point.x), y: point.y }
            })
        }  
    }

    render() {
        let invokeHandlePoints = this.handlePoints(this.state.oneOfstation)

        const options = {
			theme: "light2",
			animationEnabled: true,
			exportEnabled: true,
			title:{
				text: ""
			},
			axisY:{
				title: "Liczba rowerów",
				includeZero: false
			},
			data: [{
				type: "stepLine",
				xValueFormatString: "HH MM MMM YYYY",
				markerSize: 5,
                dataPoints: invokeHandlePoints                
            }]
		}

        return (
            <div>
            <CanvasJSChart options = {options}
				/* onRef={ref => this.chart = ref} */
			/>
			{/*You can get reference to the chart instance as shown above using onRef. This allows you to access all chart properties and methods*/}
		</div>
        );
    }
}

export default Graph;