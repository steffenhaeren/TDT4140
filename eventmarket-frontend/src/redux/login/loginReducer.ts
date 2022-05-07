import {
    LogInActionTypes,
    LOG_IN,
    LOG_OUT
  } from "./loginTypes";

// Default state set to false if jwt is either empty or does not exist 
const defaultState = sessionStorage.getItem("jwt") === null ? (false) : (sessionStorage.getItem("jwt") !== "" );

export const logInReducer = (
    state = defaultState,
    action: LogInActionTypes,
  )=> {
    switch (action.type) {
        case LOG_IN:{return true}
        case LOG_OUT:{return false}
        default:
            return state;
    }
  };
