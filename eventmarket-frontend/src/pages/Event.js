import React,{Component} from 'react';
import Navbar from './Navbar';
import '../style/style.css';


class Event extends Component {
  
    render() {
        return (
        <div>
        <Navbar />
        <h1>Event</h1>
        <h3>Overskrift</h3>
        <img src="#" alt="bilde"></img>

        <br></br>
        <label htmlFor="kategori">Kategori</label>
        <p>**KATEGORI**</p>

        <br></br>
        <label htmlFor="pris">Pris</label>
        <p>**PRIS**</p>

        <br></br>
        <label htmlFor="lokasjon">Lokasjon</label>
        <p>**LOKASJON**</p>

        <br></br>
        <label htmlFor="dato">Dato</label>
        <p>**DATO**</p>

        <br></br>
        <label htmlFor="tid">Tid</label>
        <p>**TID**</p>

        <br></br>
        <label htmlFor="beskrivelse">Beskrivelse</label>
        <p>**BESKRIVELSE**</p>

        </div>
        );
    };
}
  
export default Event;