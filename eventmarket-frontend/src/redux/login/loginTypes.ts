export const LOG_IN= "LOG_IN"; 
export const LOG_OUT = "LOG_OUT";
export interface LogInAction {
    type: "LOG_IN";
  }

export interface LogOutAction{
    type:"LOG_OUT";
}

export type LogInActionTypes =
  | LogInAction
  | LogOutAction;
