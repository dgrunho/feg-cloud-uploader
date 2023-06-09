﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using FEG_Cloud_Webservices.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.Internal;
using Microsoft.AspNetCore.Mvc;

namespace FEG_Cloud_Webservices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FilesController : ControllerBase
    {
        // GET api/files
        public ActionResult<IEnumerable<string>> Get()
        {

            object obj = Request.Query["req"];
            
            Request.EnableRewind();
            using (var reader = new StreamReader(Request.Body))
            {
                var body = reader.ReadToEnd();

                // Do something

                Request.Body.Seek(0, SeekOrigin.Begin);

                body = reader.ReadToEnd();
            }
            return new string[] { "value1", "value2" };
        }


        // POST api/files
        [HttpPost]
        public ActionResult<IEnumerable<string>> Post([FromBody] FilesCompare value)
        {
            return new string[] { "value1", "value2" };
        }
    }
}
