using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BallController : MonoBehaviour
{
    public Rigidbody2D rgb;
    public float speed;
    // Start is called before the first frame update
    void Start()
    {
        float x = Random.Range(0, 2) == 0 ? -1 : 1;
        float y = Random.Range(0, 2) == 0 ? -1 : 1;
        rgb.velocity = new Vector2(x * speed, y * speed);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
