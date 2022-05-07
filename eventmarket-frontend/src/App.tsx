import './style/style.css';
//import { createTheme } from '@mui/material';
import { Component } from 'react';
import {Link, Navigate} from 'react-router-dom';


import logo from './images/event-market-cropped-inverted.png'


//const theme = createTheme({
//palette: {
//primary: {
//main: "#d81f27" // This is an orange looking color
//},

//secondary: {
//main: "#d81f27" //Another orange-ish color
//}
//},
//});

class App extends Component {


  render() {
    let bearer = window.localStorage.getItem("bearer");
    if (bearer != null && bearer !== "") {
      return <Navigate to="/pages/explore" />
    }
    return (
      <div>
        <br></br>

        <div className="parent">
          <div className="child">
            <img src={logo} width="700px" alt="logo" />
            <p id="event-market">EventMarket</p>
          </div>

          <div className="child">
            <ul>
              <Link to="pages/signup" style={{ textDecoration: 'none' , color: '#FFFFFF', fontWeight: 700}}><li>Registrer</li></Link>
              <Link to="pages/login" style={{ textDecoration: 'none' , color: '#FFFFFF', fontWeight: 700}}><li>Logg inn</li></Link>
            </ul>
          </div>
        </div>

      </div>
    );
  }
}

export default App;
// vim:sw=2
