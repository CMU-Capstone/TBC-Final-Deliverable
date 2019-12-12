import React from "react";
// @material-ui/core components
import moment from 'moment';
import { makeStyles } from "@material-ui/core/styles";
import TextField from '@material-ui/core/TextField';
import { DateTimePicker, MuiPickersUtilsProvider } from '@material-ui/pickers';
// import MomentUtils from '@date-io/moment';
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
// Material helpers
// import { withStyles } from "@material-ui/core";
import InputLabel from "@material-ui/core/InputLabel";
// core components
import GridItem from "components/Grid/GridItem.js";
import GridContainer from "components/Grid/GridContainer.js";
import CustomInput from "components/CustomInput/CustomInput.js";
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardHeader from "components/Card/CardHeader.js";
import CardAvatar from "components/Card/CardAvatar.js";
import CardBody from "components/Card/CardBody.js";
import CardFooter from "components/Card/CardFooter.js";
import Table from "components/Table/Table.js";
import avatar from "assets/img/faces/marc.jpg";
import { Typography } from "@material-ui/core";

const styles = {
  cardCategoryWhite: {
    color: "rgba(255,255,255,.62)",
    margin: "0",
    fontSize: "14px",
    marginTop: "0",
    marginBottom: "0"
  },
  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none"
  }
};

class SearchSlack extends React.Component {
  // export default function Search() {

  constructor(props) {
    super(props);
    this.projectData = [];
    this.state = {
      isLoading: true,
      key_word:null,
      hackthon_name: null,
      start_date: Date.now(),
      end_date: Date.now(),
      channel: null,
      email: null
    };
  }

  /**
   * Retrieve projects.
   *  @param {string} queryParam
   */
  getProjects = () => {
    this.projectData = [];
    const { hackthon_name, key_word, start_date, end_date, channel, email} = this.state;
    let queryParam = "";
    // console.logs
    if (key_word !=null) { 
      if (queryParam!="") {
        queryParam = queryParam.concat("&");
      }
      queryParam=queryParam.concat( `keyword=${key_word}`);
    }
    if (hackthon_name !=null) { 
      if (queryParam!="") {
        queryParam = queryParam.concat("&");
      }
      queryParam=queryParam.concat( `hackthonName=${hackthon_name}`);
    }
    if (start_date !=null) { 
      if (queryParam!="") {
        queryParam = queryParam.concat("&");
      }
      queryParam=queryParam.concat( `start=${new Date(start_date).getTime()/1000}`);
    }
    if (end_date!=null) { 
      if (queryParam!="") {
        queryParam = queryParam.concat("&");
      }
      queryParam=queryParam.concat( `end=${new Date(end_date).getTime()/1000}`);
    }
    if (email !=null) { 
      if (queryParam!="") {
        queryParam = queryParam.concat("&");
      }
      queryParam=queryParam.concat( `email=${email}`);
    }
    if (channel !=null) { 
      if (queryParam!="") {
        queryParam = queryParam.concat("&");
      }
      queryParam=queryParam.concat( `channel=${channel}`);
    }
    // console.log(queryParam);
    // console.log(moment(new Date(new Date(start_date).getTime())).format("YYYY-MM-DD"));
    // console.log(JSON.stringify(searchPara));
    // alert(JSON.stringify(searchPara));
    // console.log(`http://18.220.249.85:8081/slack_analyser/api/search?${queryParam}`);
    fetch(process.env.REACT_APP_SLACK_BASE+`/slack_analyser/api/search?${queryParam}`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
    .then((responseJson) => {
      for (let i = 0; i < responseJson.length; i++){ 
        let list = [];
        Object.keys(responseJson[i])
          .forEach((key) => { 
            // console.log(key); // alerts key 
            if (key != "_id") {
              if (key === "timeStamp") { 
                // console.log(responseJson[i][key]);
                // console.log(moment(new Date(responseJson[i][key])).format('YYYY-MM-DD'));
                list.push(moment(new Date(parseInt(responseJson[i][key])*1000)).format('YYYY-MM-DD'));
              }else{
                list.push(responseJson[i][key]);
              }
              
            }

            // console.log(responseJson[i][key]); // alerts value
          });
        // console.log(list);
        this.projectData.push(list);
        
      }
      console.log(this.projectData);

        this.setState({ isLoading: false });
        // alert(this.projectData.length);
      })
      .catch(error => console.error(error));
  }

  renderResult = isLoading => {
    if (isLoading) {
      return (<Typography> </Typography>);
    }
     return(
      <Card>
        <CardHeader color="primary">
          <h4 className={makeStyles.cardTitleWhite}>Search Result</h4>
        </CardHeader>
         <CardBody> 
           <Table style={{height: 10,overflow: "scroll"}}
             tableHeaderColor="primary"
            //  ,"Descriptions","Achievements"
            tableHead={["Time Stamp","Hackathon Name", "Channel Name", "User Email", "Text","UserID"]}
            tableData={this.projectData}
          />
        </CardBody>
      </Card>
    );
  }

  render() {
    const { isLoading,start_date,end_date } = this.state;
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="primary">
                <h4 className={makeStyles.cardTitleWhite}>Search Projects</h4>
              </CardHeader>
              <CardBody>
                <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                  {/* const { hackthon_name, project_name, problem, industry, technology, email, header, data } = this.state; */}

                    <TextField
                      label="Key Word"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ key_word: event.target.value });
                      }}
                    />
                    {'\t'}{'\t'}{'\t'}{'\t'}
                    <TextField
                      label="Email"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ email: event.target.value });
                      }}
                    />
                  </GridItem>
                  </GridContainer>
                  <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                    {/* <MuiPickersUtilsProvider utils={MomentUtils}> */}
                    <Typography>Start Date</Typography>
                    <DatePicker
                      id={0}
                      style={{ width: 200, marginLeft: 50, marginTop: 10 }}
                      selected={start_date}
                      mode="datetime"
                      placeholder="select date"
                      format="YYYY-MM-DD h:mm a"
                      confirmBtnText="Confirm"
                      cancelBtnText="Cancel"
                      onChange={(date) => {
                        console.log(date);
                        this.setState({
                          start_date: date,
                        });
                      }}
                        />
                    {/* </MuiPickersUtilsProvider> */}
                    <Typography>End Date</Typography>
                    <DatePicker
                      id={0}
                      style={{ width: 200, marginLeft: 50, marginTop: 10 }}
                      selected={end_date}
                      mode="datetime"
                      placeholder="select date"
                      format="YYYY-MM-DD h:mm a"
                      confirmBtnText="Confirm"
                      cancelBtnText="Cancel"
                      onChange={(date) => {
            
                        this.setState({
                          end_date: date,
                        });
                      }}
                        />
                    {'\t'}
                  </GridItem>
                </GridContainer>

                <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                  <TextField
                      label="Channel"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ channel: event.target.value });
                      }}
                    />
                    {'\t'}{'\t'}
                    <TextField
                      label="Hackathon Name"
                      id="Hackathon Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ hackthon_name: event.target.value });
                      }}
                    />
                  </GridItem>
        
                </GridContainer>
          
              
                
              </CardBody>
              <CardFooter>
                <Button color="primary" onClick={() => {
                  this.getProjects()
                }}>Search</Button>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={12}>
            {this.renderResult(isLoading)}
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default SearchSlack;
