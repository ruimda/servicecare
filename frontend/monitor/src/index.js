import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { isEmpty, distance } from './util.js';

import Filter from './components/Filter.js'
import Info from './components/Info.js'
import MapContainer from './components/MapContainer.js'

class App extends React.Component {
  TRUCK_SERVICE = `${process.env.REACT_APP_TRUCK_SERVICE_SERVER}:${process.env.REACT_APP_TRUCK_SERVICE_PORT}/api/trucks`;

  constructor(props) {
    super(props);
    this.state = {
      messages: [],
      currentLocation: null,
      oldestLocation: null,
      middleLocations: null,
      poiType: null,
      poiRadius: null,
      poiData: null,
    };
  }

  mapReady = (mapProps, map) => {
    this.mapProps = mapProps;
    this.map = map;
  }

  apply = (plate, type, radius) => {
    // validate
    let messages=[];
    if (isEmpty(plate)) messages.push("Please enter License plate");
    if (isEmpty(type)) messages.push("Please select POI type");

    if (messages.length > 0) {
      this.setState({messages});
      return;
    }

    this.setState({
      poiRadius: radius,
      poiType: type.split(','),
      messages : ["Loading ..."]
    });

    // Get truck (initial call)
    fetch(`${this.TRUCK_SERVICE}?licensePlate=${plate}`)
      .then(response => {
        if (response.status === 404) {
          this.setState({messages: ["Truck NOT FOUND"]})
          return null;
        }
        return response.json();
      })
      .then(this.processTruckReponse)
      .catch(this.manageError);
  }

  processTruckReponse = (data) => {
    if (data == null) return;
    this.setState({
      messages:["truck: "+data.brand + " - "+data.model]
    });

    fetch(`${this.TRUCK_SERVICE}/${data.id}/locations`)
      .then(res => res.json())
      .then(this.processLocationsReponse)
      .catch(this.manageError);
  }

  processLocationsReponse = (data) => {
    let locations = [];
    data.forEach(element => {
      locations.push({ lat: element.latitude, lng: element.longitude });
    });

    let newState = {}
    if (locations.length > 0) newState['oldestLocation'] = locations.pop();
    if (locations.length > 0) newState['currentLocation'] = locations.shift();
    newState['middleLocations'] = locations;

    this.setState( newState );

    this.fetchPlacesOfInterest();
  }

  fetchPlacesOfInterest = () => {
    let poiData = [];
    this.setState({ poiData });

    const {google} = this.mapProps;
    const placesService = new google.maps.places.PlacesService(this.map);
    var location = new google.maps.LatLng(this.state.currentLocation.lat, this.state.currentLocation.lng);

    for (let i=0; i<this.state.poiType.length; i++) {
      let type = this.state.poiType[i];
      let request = { location, type };
      if (this.state.poiRadius)
        request['radius'] = this.state.poiRadius;
      else
        request['rankBy'] = google.maps.places.RankBy.DISTANCE;

      placesService.nearbySearch(request, (places, status) => {
          if (status === google.maps.places.PlacesServiceStatus.OK) {
            places.forEach(place => {
              poiData.push( this.getPoiDataElementFromPlace( place, type ) );
            });
            this.setState({ poiData });
          }
        });
      }
  }

  getPoiDataElementFromPlace = (place, type) => {
    let tooltip = place.name;

    if ('opening_hours' in place) {
      let status = place.opening_hours.open_now ? 'open' : 'closed';
      tooltip = `${tooltip}\n${status} now`;
    }

    if ('rating' in place)
      tooltip = `${tooltip}\nrating: ${place.rating}`;

    if ('price_level' in place) {
      let level = ['Free','Inexpensive','Moderate','Expensive','Very Expensive'][place.price_level];
      tooltip = `${tooltip}\nprice: ${level}`;
    }

    let position = {
      lat: place.geometry.location.lat(),
      lng: place.geometry.location.lng()
    };

    let dist = distance(this.state.currentLocation, position);
    tooltip = `${tooltip}\ndistance: ${dist.toFixed(3)} Km`;

    let element = {
      position,
      title: tooltip,
      icon: `/assets/icn-${type}.png`,
      search: `${type}+"${place.name}"`
    }

    return element;
  }

  manageError = (error) => {
    this.setState({messages: [ "Problem communication with Server" ]});
  }

  render() {
    return (
      <div>
      <Filter onApply={this.apply} />

      <Info messages={this.state.messages} />

      <MapContainer
        google={this.props.google}
        zoom={15}
        mapReady={this.mapReady}

        currentLocation={this.state.currentLocation}
        oldestLocation={this.state.oldestLocation}
        middleLocations={this.state.middleLocations}
        poiRadius={this.state.poiRadius}
        poiType={this.state.poiType}
        poiData={this.state.poiData}
        >
      </MapContainer>
      </div>
    );
  }
}


ReactDOM.render(<App />, document.getElementById("root"));