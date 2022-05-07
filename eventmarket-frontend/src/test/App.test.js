import { render } from '@testing-library/react';
import App from '../App';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

test('TODO', () => {
    render(
    <BrowserRouter>
        <Routes>   
            <Route path="*" element= {<App />}/>
        </Routes>
    </BrowserRouter>
    );
});
