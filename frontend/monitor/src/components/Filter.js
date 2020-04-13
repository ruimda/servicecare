import React from 'react';

export default class Filter extends React.Component {

    constructor(props) {
      super(props);
      this.state = {
        plate: '',
        poi: '',
        radius: ''
      };
    }

    myChangeHandler = (event) => {
      let nam = event.target.name;
      let val = event.target.value;
      this.setState({[nam]: val});
    }

    mySubmitHandler = (event) => {
        event.preventDefault();
        this.props.onApply(this.state.plate, this.state.poi, this.state.radius);
      }

    render() {
      return (
        <div className="filters">
        <form onSubmit={this.mySubmitHandler}>

        <input name="plate" className="filter" type="text" placeholder="Search by license plate" onChange={this.myChangeHandler}>
        </input>
        <select name="poi" className="filter" id="poi" placeholder="Select POI type" onChange={this.myChangeHandler}>
            <option value="">Select POI type</option>
            <option value="gas_station,restaurant,hotel">View All</option>
            <option value="gas_station">Gas Stations</option>
            <option value="restaurant">Restaurants</option>
            <option value="hotel">Hotels</option>
        </select>
        <select name="radius" className="filter" id="poi" placeholder="Select radius" onChange={this.myChangeHandler}>
            <option value="">Select Radius</option>
            <option value="1000">1 Km</option>
            <option value="3000">3 Km</option>
            <option value="5000">5 Km</option>
            <option value="10000">10 Km</option>
        </select>
        <button type='submit' className="applyButton">Apply</button>

        </form>
        </div>
      );
    }
}