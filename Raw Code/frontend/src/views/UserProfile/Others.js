import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import TextField from '@material-ui/core/TextField';
// Material helpers
// import { withStyles } from "@material-ui/core";
import InputLabel from "@material-ui/core/InputLabel";
import Select from 'react-select';
// import Button from "components/CustomButtons/Button.js";
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

class Others extends React.Component {
  // export default function Search() {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
    };
  }

  componentWillMount = () => { 
  
  }


  render() {
    const { isLoading } = this.state;
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="primary">
                <h4 className={makeStyles.cardTitleWhite}>Directory</h4>
              </CardHeader>
              <CardBody>
                <Button
         
                  onClick={() => {
                  window.open(process.env.REACT_APP_DEMO_BASE+"/swagger-ui.html#/", "_blank") //to open new page
                }}>
               Demo
               </Button>
               <Button
        
                  onClick={() => {
                  window.open(process.env.REACT_APP_GIT_BASE+"/swagger-ui.html#/", "_blank") //to open new page
                }}>
               GitHub Scanner
               </Button>
               <Button
        
                    onClick={() => {
                    window.open(process.env.REACT_APP_SLACK_BASE+"/swagger-ui.html#/", "_blank") //to open new page
                  }}>
                Slack
                </Button>

               
              </CardBody>
             
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default Others;
