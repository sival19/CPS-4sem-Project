﻿using Newtonsoft.Json.Linq;
using RestSharp;
using System;
using System.IO;
using System.Net;
using System.Threading;
using System.Text.Json;
using System.Threading.Tasks;

namespace AGV
{
    public class REST
    {
        public REST()
        {
        }

        //init REST
        private RestClient client = new RestClient("http://localhost:8082");
        private RestRequest request = new RestRequest("v1/status/");
        private string url = "http://localhost:8082/v1/status/";

        //runner
        // public async Task RunExample()
        // {
        //     GetStatus();
        //     PutOperation();
        // }
        
        
        //test PUT request
        public async void PutOperation(string name, int state, bool onlyStatus)
        {
            string messageBody;
            if (!onlyStatus)
            {
                messageBody = "{" +
                              "\"program name\": " +
                              "\"" + name + "\"," +
                              "\"state\": " + state.ToString()+"}";
            }
            else
            {
                messageBody = "{" +
                              "\"state\": " + state.ToString()+"}";
            }
            
            // string messageBody = "{" +
            //                      "\"program name\": " +
            //                      "\"" + name + "\"," +
            //                      "\"state\": " + state.ToString()+"}";

            var httpRequest = (HttpWebRequest)WebRequest.Create(url);
            
            httpRequest.Method = "PUT";

            httpRequest.ContentType = "application/json";
            
            using (var streamWriter = new StreamWriter(httpRequest.GetRequestStream()))
            {
                streamWriter.Write(messageBody);
            }

            var httpResponse = (HttpWebResponse)httpRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
            
        }
        public async void PutOperation(int state)
        {
            string messageBody = "{" +
                                 "\"state\": " + state+"}";

            var httpRequest = (HttpWebRequest)WebRequest.Create(url);
            
            httpRequest.Method = "PUT";

            httpRequest.ContentType = "application/json";
            
            using (var streamWriter = new StreamWriter(httpRequest.GetRequestStream()))
            {
                streamWriter.Write(messageBody);
            }

            var httpResponse = (HttpWebResponse)httpRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
            
        }

        //test status method
        /// <summary>
        /// Make a get request for AGV using REST protocol
        /// </summary>
        /// <param name="request">Get Request</param>
        /// <returns></returns>
        public async Task<string> GetRequest(string request)
        {
            //GET request
            RestResponse response = await client.GetAsync(new RestRequest(request));
            string s = response.Content;
            return s;
        }
    }

    //class to serialize json objects
    // public class OperationMessage
    // {
    //     //tag forces the name of the json attribute on serialization to the specified PropertyName
    //     [JsonProperty(PropertyName = "program name")]
    //     public string Program_name { get; set; }
    //
    //     public int State { get; set; }
    // }
}