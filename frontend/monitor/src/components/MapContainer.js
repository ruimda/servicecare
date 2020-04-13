import React from 'react';
import {Map, Marker, GoogleApiWrapper} from 'google-maps-react';

export class MapContainer extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      truckLocations: null,
      poiType: null,
      poiRadius: null
    };
  }

  onMarkerClick(props, marker, e) {
    let URL = `http://www.google.com/search?q=${marker.name}`;
    window.open(URL, '_blank');
  }

  render() {
      let currentLocationMarker=''
      if (this.props.currentLocation) {
        currentLocationMarker = <Marker title={'Truck'}  name={'truck'} position={ this.props.currentLocation }
                      icon={{ url: "/assets/icn-current-location.png" }} />
      }

      let oldestLocationMarker=''
      if (this.props.oldestLocation) {
        oldestLocationMarker = <Marker position={ this.props.oldestLocation } icon={{ url: "/assets/icn-first-location.png" }} />
      }

      let middleLocationMarkers=[]
      let key = 1;
      if (this.props.middleLocations) {
        this.props.middleLocations.forEach(element => {
          key++;
          let marker = <Marker position={ element } icon={{ url: "/assets/icn-path.png" }} key={ key } />
          middleLocationMarkers.push( marker )
        });
      }

      let poiMarkers=[]
      if (this.props.poiData != null) {
        this.props.poiData.forEach(element => {
          key++;
          let marker = <Marker position={ element.position } icon={{ url: element.icon }} title={element.title}
                               onClick={this.onMarkerClick} name={element.search} key={ key } />
          poiMarkers.push( marker )
        });
      }

      return (
        <Map
          google={this.props.google}
          zoom={this.props.zoom}
          center={this.props.currentLocation}
          onReady={this.props.mapReady}
          >
          {currentLocationMarker}
          {oldestLocationMarker}
          {middleLocationMarkers}
          {poiMarkers}

        </Map>
      );
    }
}

export default GoogleApiWrapper({
  apiKey: process.env.REACT_APP_GOOGLE_API_KEY
})(MapContainer)